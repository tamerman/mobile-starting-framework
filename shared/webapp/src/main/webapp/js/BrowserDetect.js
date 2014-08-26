/*
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


/**
 * Author        : Charl Thiem
 * Created        : 2013-07-04
 * Credits        : This script is inspired from http://www.quirksmode.org/js/detect.html
 *
 * A script to do various UserAgent Detection
 *
 * This plugin will only check for user agent details once this first method is called (lazy initialise)
 * Therefore this script is safe to attach to every page as it does not do heavy calculations at load time.
 *
 * Usage:
 *        The tool will be available on window.kme.browserDetect
 *
 * Functions:
 *        isTablet()                        - Returns true if the browser is a tablet
 *        isDesktop()                        - Returns true if the browser is from a desktop
 *        isMobile()                        - Returns true if the browser is from a mobile (not desktop)
 *        isOS(string osName)                - Returns true if the browser is of the specified OS
 *        isBrowser(string browserName)    - Returns true if the browser is the specified browser name
 *        isNative()                        - Returns true if the browser is from a native app
 *        isOSNative(string osName)        - Returns true if the browser is from a native app and is the specified OS
 *        getBrowser()                    - Returns the name of the browser (matches a value from BrowserDetect.browsers)
 *        getOS()                            - Returns the name of the OS (matches a value from BrowserDetect.OSes)
 *
 * Fields:
 *        BrowserDetect.browsers            - Contains various browser names that can be detected
 *        BrowserDetect.OSes                - Contains various browser OSes that can be detected
 */

(function (window) {


    // Define all the browsers
    BrowserDetect.browsers = {};
    BrowserDetect.browsers.IEMobile = "IEMobile";
    BrowserDetect.browsers.Chrome = "Chrome";
    BrowserDetect.browsers.Safari = "Safari";
    BrowserDetect.browsers.Opera = "Opera";
    BrowserDetect.browsers.Firefox = "Firefox";
    BrowserDetect.browsers.IE = "IE";


    // Define all the OSes
    // IMPORTANT - These values must match the values (and case) of platforms detected inside of KME
    BrowserDetect.OSes = {};
    BrowserDetect.OSes.iOS = "iOS";
    BrowserDetect.OSes.iPad = "iPad";
    BrowserDetect.OSes.iPod = "iPod";
    BrowserDetect.OSes.iPhone = "iPhone";
    BrowserDetect.OSes.BlackBerry = "BlackBerry";
    BrowserDetect.OSes.Windows = "Windows";
    BrowserDetect.OSes.WindowsMobile = "WindowsMobile";
    BrowserDetect.OSes.Mac = "Mac";
    BrowserDetect.OSes.Android = "Android";
    BrowserDetect.OSes.Linux = "Linux";
    BrowserDetect.OSes.Symbian = "Symbian";
    BrowserDetect.OSes.Nokia = "Nokia";
    BrowserDetect.OSes.webOS = "webOS";


    /**
     * Contructor
     */
    function BrowserDetect() {
        this.initialised = false;
        this._setupData(); // TODO move this to lazy too ?
    }

    /**
     * Returns true if the platform is a tablet
     */
    BrowserDetect.prototype.isTablet = function () {
        this._init();
        // Charl - Not very in favor of these tests, maybe check something else?
        return this.isOS(BrowserDetect.OSes.iPad) ||
            (navigator.userAgent.indexOf("GT-P1000") != -1) ||
            (navigator.userAgent.indexOf("GT-P1000 Build/FROYO") != -1) ||
            (navigator.userAgent.indexOf("GT-P1000R Build/FROYO") != -1) ||
            (navigator.userAgent.indexOf("GT-P1000M Build/FROYO") != -1) ||
            (navigator.userAgent.indexOf("SGH-T849 Build/FROYO") != -1) ||
            (navigator.userAgent.indexOf("SHW-M180S Build/FROYO") != -1) ||
            (navigator.userAgent.indexOf("GT-P1000T Build/FROYO") != -1) ||
            (navigator.userAgent.indexOf("playbook") != -1) ||
            (navigator.userAgent.indexOf("tablet") != -1) ||
            (navigator.userAgent.indexOf("kindle") != -1) ||
            (navigator.userAgent.indexOf("xoom") != -1);
    };

    /**
     *  Returns true if the OS is the specified OS.
     * @param os The OS to test for.
     * @returns {Boolean} True if the os is the specified os.
     */
    BrowserDetect.prototype.isOS = function (os) {
        return this.getOS() == os;
    };

    /**
     * Gets the OS
     */
    BrowserDetect.prototype.getOS = function () {
        this._init();
        return this.OS;
    };


    /**
     * Returns true if the Browser is from an iOS devices (iPod, iPhone, iPad)
     */
    BrowserDetect.prototype.isIOS = function () {
        return this.isOS(BrowserDetect.OSes.iPhone) || this.isOS(BrowserDetect.OSes.iPad) || this.isOS(BrowserDetect.OSes.iPod);
    };

    /**
     * Returns true if the UserAgent is a desktop
     */
    BrowserDetect.prototype.isDesktop = function () {
        return this.isOS(BrowserDetect.OSes.Windows) || this.isOS(BrowserDetect.OSes.Mac) || (this.isOS(BrowserDetect.OSes.Linux) && !this.isOS(BrowserDetect.OSes.Android));
    };

    /**
     * Returns trye if the browser is a mobile device
     */
    BrowserDetect.prototype.isMobile = function () {
        // If it is not desktop, it must be mobile
        return !this.isDesktop();
    };

    /**
     * Returns true if the UserAgent is native and the specified OS
     * @param os OS to test for
     * @returns {Boolean} True if the UserAgent is native and the specified OS
     */
    BrowserDetect.prototype.isNativeOS = function (os) {
        return this.isNative() && this.isOS(os);
    };

    /**
     * Returns true if the detected browser is the specified browser.
     * @param browser The browser to test for.
     * @returns {Boolean} True if the browser is the specified browser.
     */
    BrowserDetect.prototype.isBrowser = function (browser) {
        return this.getBrowser() == browser;
    };

    /**
     * Gets the name of the browser
     */
    BrowserDetect.prototype.getBrowser = function () {
        this._init();
        return this.browser;
    };

    /**
     * Returns true if the coookie "native, is yes
     * @returns
     */
    BrowserDetect.prototype.isNative = function () {
        return $.cookie("native") == "yes";
    };

    /**
     * Sets up the data
     */
    BrowserDetect.prototype._setupData = function () {

        this.dataBrowser = [
            {
                string: navigator.userAgent,
                subString: "Chrome",
                identity: BrowserDetect.browsers.Chrome
            },

            {
                prop: window.opera,
                identity: BrowserDetect.browsers.Opera,
                versionSearch: "Version"
            },
            {
                string: navigator.userAgent,
                subString: "Firefox",
                identity: BrowserDetect.browsers.Firefox
            },
            {
                string: navigator.userAgent,
                subString: "MSIE",
                identity: BrowserDetect.browsers.IE,
                versionSearch: "MSIE"
            },
            {
                string: navigator.userAgent,
                subString: "IEMobile",
                identity: BrowserDetect.browsers.IEMobile
            },
            {
                string: navigator.vendor,
                subString: "Apple",
                identity: BrowserDetect.browsers.Safari,
                versionSearch: "Version"
            }
        ];
        this.dataOS = [
            {
                string: navigator.userAgent,
                regex: "webOS",
                identity: BrowserDetect.OSes.webOS
            },
            {
                string: navigator.userAgent,
                regex: /BlackBerry/i,
                identity: BrowserDetect.OSes.BlackBerry
            },
            {
                string: navigator.userAgent,
                subString: "IEMobile",
                identity: BrowserDetect.OSes.WindowsMobile
            },
            {
                string: navigator.platform,
                subString: "Win",
                identity: BrowserDetect.OSes.Windows
            },
            {
                string: navigator.platform,
                subString: "Mac",
                identity: BrowserDetect.OSes.Mac
            },
            {
                string: navigator.userAgent,
                subString: "iPhone",
                identity: BrowserDetect.OSes.iPhone
            },
            {
                string: navigator.userAgent,
                subString: "iPad",
                identity: BrowserDetect.OSes.iPad
            },
            {
                string: navigator.userAgent,
                subString: "iPod",
                identity: BrowserDetect.OSes.iPod
            },
            {
                string: navigator.platform,
                regex: /Android/i,
                identity: BrowserDetect.OSes.Android
            },
            {
                string: navigator.platform,
                subString: "Linux",
                identity: BrowserDetect.OSes.Linux
            },
            {
                string: navigator.platform,
                subString: "Symbian",
                identity: BrowserDetect.OSes.Symbian
            }
        ];
    };


    /**
     * Initialise the BrowserDetect by searching the UserAgent details
     */
    BrowserDetect.prototype._init = function () {
        if (this.initialised) {
            return;
        }

        this.browser = this._searchString(this.dataBrowser) || "An unknown browser";
        this.version = this._searchVersion(navigator.userAgent)
            || this._searchVersion(navigator.appVersion)
            || "an unknown version";
        this.OS = this._searchString(this.dataOS) || "an unknown OS";
        this.initialised = true;
    };

    /**
     *
     * @param data
     * @returns
     */
    BrowserDetect.prototype._searchString = function (data) {
        for (var i = 0; i < data.length; i++) {

            this.versionSearchString = data[i].versionSearch || data[i].identity;
            if (data[i].regex && navigator.userAgent.match(data[i].regex)) {
                return data[i].identity;
            }
            if (data[i].string && (data[i].string.indexOf(data[i].subString) != -1)) {
                return data[i].identity;
            }
            else if (data[i].prop) {
                return data[i].identity;
            }
        }
    };

    /**
     * Searches for the version of a UserAgent
     * @param dataString
     * @returns
     */
    BrowserDetect.prototype._searchVersion = function (dataString) {
        var index = dataString.indexOf(this.versionSearchString);
        if (index == -1) return;
        return parseFloat(dataString.substring(index + this.versionSearchString.length + 1));
    };

    /**
     * Add an instance of this script to window.kme.browserDetect
     */
    window.kme = window.kme || {};
    window.kme.browserDetect = window.kme.browserDetect || new BrowserDetect();
    window.BrowserDetect = BrowserDetect;

})(window);
