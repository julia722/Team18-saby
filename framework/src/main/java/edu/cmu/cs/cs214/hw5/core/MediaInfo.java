package edu.cmu.cs.cs214.hw5.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaInfo {

    private final String title;
    private final int productionYear;
    private final String creator;
    private final List<String> reviews;
    private final String url;
    private final String genre;
    private final Map<String, Integer> sentimentData = new HashMap<>();

    public MediaInfo(String title, int productionYear, String creator, List<String> reviews, String url, String genre) {
        this.title = title;
        this.productionYear = productionYear;
        this.creator = creator;
        this.reviews = reviews;
        this.url = url;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getCreator() {
        return creator;
    }

    public List<String> getReviews() {
        return List.copyOf(reviews);
    }

    public String getUrl() {
        return url;
    }

    public String getGenre() {
        return genre;
    }

    public Map<String, Integer> getSentimentData() {
        return Map.copyOf(sentimentData);
    }

    public void setSentimentData(Map<String, Integer> sentimentData) {
        for(Map.Entry<String, Integer> entry : sentimentData.entrySet()) {
            this.sentimentData.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "title: " + title + "\nyear: " + productionYear + "\ncreator: " + creator +
                "\n# of reviews: " + reviews.size() +
                "\nfirst review: " + reviews.get(0) +
                "\nurl of a video: " + url +
                "\ngenre: " + genre +
                "\nsentimentData: " + sentimentData;
    }

}