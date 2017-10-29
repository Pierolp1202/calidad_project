package com.example.pierolpw10.serviexpress.Models;

/**
 * Created by NriKe on 29/10/2017.
 */

public class Work {
    String username;
    String worker;
    String date;
    int rating = 0;
    int work;
    String worker_image;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public String getWorker_image() {
        return worker_image;
    }

    public void setWorker_image(String worker_image) {
        this.worker_image = worker_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
