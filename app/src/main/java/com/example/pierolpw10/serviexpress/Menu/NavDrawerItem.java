package com.example.pierolpw10.serviexpress.Menu;

/** Created by rubymobile on 16/11/15.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int resourceId;
    private boolean withImage;
    private boolean lastItemwithImage;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public NavDrawerItem(String title, boolean withImage, boolean lastItemwithImage, int resourceId) {
        this.title = title;
        this.withImage = withImage;
        this.resourceId = resourceId;
        this.lastItemwithImage = lastItemwithImage;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public boolean isWithImage() {
        return withImage;
    }

    public void setWithImage(boolean withImage) {
        this.withImage = withImage;
    }

    public boolean isLastItemwithImage() {
        return lastItemwithImage;
    }

    public void setLastItemwithImage(boolean lastItemwithImage) {
        this.lastItemwithImage = lastItemwithImage;
    }
}
