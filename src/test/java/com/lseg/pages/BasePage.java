package com.lseg.pages;

import com.lseg.helpers.CommonActions;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.Playwright.create;



public abstract class BasePage extends CommonActions {

    /**
     * Page
     */
    protected Browser browser;
    protected Page page;

    public Page createPlaywrightPageInstance(String browserTypeAsString) {
		
        BrowserType browserType;
        browserType = switch (browserTypeAsString) {
            case "Firefox" ->
                create().firefox();
            case "Chromium" ->
                create().chromium();
            case "Webkit" ->
                create().webkit();
            default ->
                null;
        };
        if (browserType == null) {
            throw new IllegalArgumentException("Could not launch a browser for type " + browserTypeAsString);
        }
		browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false).setChannel("msedge"));
		BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
       
        page = context.newPage();
        return page;

    }

}
