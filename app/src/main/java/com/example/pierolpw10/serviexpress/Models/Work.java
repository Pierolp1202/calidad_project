package com.example.pierolpw10.serviexpress.Models;

/**
 * Created by NriKe on 29/10/2017.
 */

public class Work {
    String username;
    String worker;
    String date;
    int work;
    String worker_image;
    boolean rated;
    int work_rate;

    public int getWork_rate() {
        return work_rate;
    }

    public void setWork_rate(int work_rate) {
        this.work_rate = work_rate;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
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
