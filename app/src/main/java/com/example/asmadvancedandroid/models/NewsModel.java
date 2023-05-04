package com.example.asmadvancedandroid.models;

public class NewsModel {
    private String title,
            profile_image,
            link,
            display_name;

    public NewsModel(
            String title,
            String profile_image ,
            String link, String display_name) {
        this.title = title;
        this.profile_image = profile_image;
        this.link = link;
        this.display_name = display_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
    //        private String title, description, url, urlToImage;
//
//    public NewsModel(String title, String description, String url, String urlToImage) {
//        this.title = title;
//        this.description = description;
//        this.url = url;
//        this.urlToImage = urlToImage;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getUrlToImage() {
//        return urlToImage;
//    }
//
//    public void setUrlToImage(String urlToImage) {
//        this.urlToImage = urlToImage;
//    }
}
