/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package org.kuali.mobility.admin.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.group.entity.GroupImpl;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service for actually performing administrative tasks
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Service
public class MembershipServiceImpl implements MembershipService,
		ApplicationContextAware {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(MembershipServiceImpl.class);

	private ApplicationContext applicationContext;

	@Context
	private MessageContext messageContext;

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	/**
	 * A reference to the <code>userDao</code>.
	 */
	@Resource(name = "kmeUserDao")
	private UserDao userDao;

	/**
	 * A reference to the <code>groupDao</code>.
	 */
	@Resource(name = "kmeGroupDao")
	private GroupDao groupDao;

	@GET
	@Path("/ping/get")
	public String pingGet() {
		return "{\"status\":\"OK\"}";
	}

	@POST
	@Path("/ping/post")
	public String pingPost() {
		return "{\"status\":\"OK\"}";
	}

	@GET
	@Path("/groups")
	public String getAllGroups() {
		String json = "";
		List<Group> groups = getGroupDao().getGroups();
		if (groups != null && groups.size() != 0) {
			Iterator<Group> it = groups.iterator();
			while (it.hasNext()) {
				Group group = it.next();
				json += this.toGroupJson(group) + ",";
			}
			json = json.substring(0, json.length() - 1);
		}
		return "{\"group\":[" + json + "]}";
	}

	@GET
	@Path("/userByGroup/{groupId}")
	public String getUserByGroup(@PathParam(value = "groupId") Long groupId) {
		String json = "";
		List<User> users = getUserDao().loadUserByGroup(groupId);
		if (users != null && users.size() != 0) {
			Iterator<User> it = users.iterator();
			while (it.hasNext()) {
				User user = it.next();
				json += this.toUserJson(user) + ",";
			}
			json = json.substring(0, json.length() - 1);
		}
		return "{\"user\":[" + json + "]}";
	}

	@GET
	@Path("/getAllExceptGroup/{groupId}")
	public String getAllExceptGroup(@PathParam(value = "groupId") Long groupId) {
		String json = "";

		List<User> users = getUserDao().loadAllUsers();
		if (users != null && users.size() != 0) {
			Iterator<User> it = users.iterator();
			while (it.hasNext()) {
				User user = it.next();
				if (groupId != null) {
					boolean exists = false;
					for (Group group : user.getGroups()) {
						if (groupId.equals(group.getId())) {
							exists = true;
							break;
						}
					}
					if (!exists) {
						json += this.toUserJson(user) + ",";
					}
				} else {
					json += this.toUserJson(user) + ",";
				}
			}
			if (json.length() != 0) {
				json = json.substring(0, json.length() - 1);
			}
		}
		return "{\"user\":[" + json + "]}";
	}

	@POST
	@Path("/removeGroups")
	@Transactional
	public Response removeGroups(@RequestBody String data) {
		if (data == null) {
			return Response.status(Response.Status.NO_CONTENT.getStatusCode())
					.build();
		}

		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			LOG.info(queryParams.toString());
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.build();
		}

		try {

			HttpServletRequest request;
			if (getMessageContext() != null) {
				request = (HttpServletRequest) getMessageContext()
						.getHttpServletRequest();
			} else {
				request = (HttpServletRequest) PhaseInterceptorChain
						.getCurrentMessage().get("HTTP.REQUEST");
			}
			if (null == request || null == request.getSession()) {
				LOG.error("request==null, quit!");
				return Response.status(
						Response.Status.UNAUTHORIZED.getStatusCode()).build();
			} else {
				HttpSession session = request.getSession();
				User user = (User) session
						.getAttribute(AuthenticationConstants.KME_USER_KEY);
				if (user.isMember("KME-ADMINISTRATORS")) {

					JSONArray removedGroupsJson = queryParams
							.getJSONArray("group");

					List<Group> groups = new ArrayList<Group>();
					for (int index = 0; index < removedGroupsJson.size(); index++) {
						JSONObject removedUserJson = (JSONObject) removedGroupsJson
								.get(index);
						GroupImpl group = new GroupImpl();
						group.setId(removedUserJson.getLong("id"));

						groups.add(group);
					}

					for (Group group : groups) {
						getGroupDao().removeGroup(group);
					}
				} else {
					LOG.error("user not authorized, quit!");
					return Response.status(
							Response.Status.UNAUTHORIZED.getStatusCode())
							.build();
				}
			}
		} catch (Exception e) {
			LOG.error("Exception while trying to remove group", e);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).build();
	}

	@POST
	@Path("/removeUserFromGroup")
	@Transactional
	public Response removeUserFromGroup(@RequestBody String data) {
		if (data == null) {
			return Response.status(Response.Status.NO_CONTENT.getStatusCode())
					.build();
		}

		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			LOG.info(queryParams.toString());
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.build();
		}

		try {
			HttpServletRequest request;
			if (getMessageContext() != null) {
				request = (HttpServletRequest) getMessageContext()
						.getHttpServletRequest();
			} else {
				request = (HttpServletRequest) PhaseInterceptorChain
						.getCurrentMessage().get("HTTP.REQUEST");
			}
			if (null == request || null == request.getSession()) {
				LOG.error("request==null, quit!");
				return Response.status(
						Response.Status.UNAUTHORIZED.getStatusCode()).build();
			} else {
				HttpSession session = request.getSession();
				User user = (User) session
						.getAttribute(AuthenticationConstants.KME_USER_KEY);
				if (user.isMember("KME-ADMINISTRATORS")) {

					Group group = new GroupImpl();
					JSONObject groupJson = queryParams.getJSONObject("group");
					group.setId(groupJson.getLong("id"));
					group.setName(groupJson.getString("name"));
					group.setDescription(groupJson.getString("description"));

					JSONArray removedUsersJson = queryParams
							.getJSONArray("users");

					List<User> users = new ArrayList<User>();
					for (int index = 0; index < removedUsersJson.size(); index++) {
						JSONObject removedUserJson = (JSONObject) removedUsersJson
								.get(index);
						UserImpl userlocal = new UserImpl();
						userlocal.setId(removedUserJson.getLong("id"));

						users.add(userlocal);
					}

					getGroupDao().removeFromGroup(users, group.getId());
				} else {
					LOG.error("user not authorized, quit!");
					return Response.status(
							Response.Status.UNAUTHORIZED.getStatusCode())
							.build();
				}
			}

		} catch (Exception e) {
			LOG.error("Exception while trying to remove user from group", e);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).build();
	}

	@POST
	@Path("/addUserToGroup")
	@Transactional
	public Response addUserToGroup(@RequestBody String data) {
		if (data == null) {
			return Response.status(Response.Status.NO_CONTENT.getStatusCode())
					.build();
		}

		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			LOG.info(queryParams.toString());
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.build();
		}

		try {
			HttpServletRequest request;
			if (getMessageContext() != null) {
				request = (HttpServletRequest) getMessageContext()
						.getHttpServletRequest();
			} else {
				request = (HttpServletRequest) PhaseInterceptorChain
						.getCurrentMessage().get("HTTP.REQUEST");
			}
			if (null == request || null == request.getSession()) {
				LOG.error("request==null, quit!");
				return Response.status(
						Response.Status.UNAUTHORIZED.getStatusCode()).build();
			} else {
				HttpSession session = request.getSession();
				User user = (User) session
						.getAttribute(AuthenticationConstants.KME_USER_KEY);
				if (user.isMember("KME-ADMINISTRATORS")) {

					Group group = new GroupImpl();
					JSONObject groupJson = queryParams.getJSONObject("group");
					group.setId(groupJson.getLong("id"));
					group.setName(groupJson.getString("name"));
					group.setDescription(groupJson.getString("description"));

					JSONArray addedUsersJson = queryParams
							.getJSONArray("users");

					List<User> users = new ArrayList<User>();
					for (int index = 0; index < addedUsersJson.size(); index++) {
						JSONObject addedUserJson = (JSONObject) addedUsersJson
								.get(index);
						UserImpl userlocal = new UserImpl();
						userlocal.setId(addedUserJson.getLong("id"));

						users.add(userlocal);
					}
					getGroupDao().addToGroup(users, group.getId());
				} else {
					LOG.error("user not authorized, quit!");
					return Response.status(
							Response.Status.UNAUTHORIZED.getStatusCode())
							.build();
				}
			}

		} catch (Exception e) {
			LOG.error("Exception while trying to remove user from group", e);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).build();
	}

	@POST
	@Path("/addGroup")
	@Transactional
	public Response addGroup(@RequestBody String data) {
		if (data == null) {
			return Response.status(Response.Status.NO_CONTENT.getStatusCode())
					.build();
		}

		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			LOG.info(queryParams.toString());
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.build();
		}

		try {
			HttpServletRequest request;
			if (getMessageContext() != null) {
				request = (HttpServletRequest) getMessageContext()
						.getHttpServletRequest();
			} else {
				request = (HttpServletRequest) PhaseInterceptorChain
						.getCurrentMessage().get("HTTP.REQUEST");
			}
			if (null == request || null == request.getSession()) {
				LOG.error("request==null, quit!");
				return Response.status(
						Response.Status.UNAUTHORIZED.getStatusCode()).build();
			} else {
				HttpSession session = request.getSession();
				User user = (User) session
						.getAttribute(AuthenticationConstants.KME_USER_KEY);
				if (user.isMember("KME-ADMINISTRATORS")) {

					Group group = new GroupImpl();
					group.setName(queryParams.getString("groupName"));
					group.setDescription(queryParams
							.getString("groupDescription"));

					getGroupDao().saveGroup(group);

				} else {
					LOG.error("user not authorized, quit!");
					return Response.status(
							Response.Status.UNAUTHORIZED.getStatusCode())
							.build();
				}
			}
		} catch (Exception e) {
			LOG.error("Exception while trying to add group", e);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
					.build();
		}

		return Response.status(Response.Status.OK.getStatusCode()).build();
	}

	private String toGroupJson(Group group) {
		String str = "{";
		str += "\"id\":\"" + group.getId() + "\",";
		str += "\"name\":\"" + group.getName() + "\",";
		str += "\"description\":\"" + group.getDescription() + "\"}";
		return str;
	}

	private String toUserJson(User user) {
		String str = "{";
		str += "\"id\":\"" + user.getId() + "\",";
		str += "\"loginName\":\"" + user.getLoginName() + "\",";
		str += "\"displayName\":\"" + user.getDisplayName() + "\",";

		String groupsJson = "";
		List<Group> groups = user.getGroups();
		if (groups != null && groups.size() != 0) {
			Iterator<Group> it = groups.iterator();
			while (it.hasNext()) {
				Group group = it.next();
				groupsJson += this.toGroupJson(group) + ",";
			}
			groupsJson = groupsJson.substring(0, groupsJson.length() - 1);
		}

		str += "\"groups\":[" + groupsJson + "]}";

		return str;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	/**
	 * @return the messageContext
	 */
	public MessageContext getMessageContext() {
		return messageContext;
	}

	/**
	 * @param messageContext
	 *            the messageContext to set
	 */
	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

}
