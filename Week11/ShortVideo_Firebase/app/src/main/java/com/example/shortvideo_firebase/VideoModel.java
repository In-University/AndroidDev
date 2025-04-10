package com.example.shortvideo_firebase;
import java.io.Serializable;
import java.util.List;

public class VideoModel implements Serializable {
    private int id;
    private String title;
    private String description;
    private String url;

    // Constructors
    public VideoModel() {}

    public VideoModel(int id, String title, String description, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Inner class
    public static class MessageVideoModel implements Serializable {
        private boolean success;
        private String message;
        private List<VideoModel> result;

        public MessageVideoModel() {}

        public MessageVideoModel(boolean success, String message, List<VideoModel> result) {
            this.success = success;
            this.message = message;
            this.result = result;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<VideoModel> getResult() {
            return result;
        }

        public void setResult(List<VideoModel> result) {
            this.result = result;
        }
    }
}
