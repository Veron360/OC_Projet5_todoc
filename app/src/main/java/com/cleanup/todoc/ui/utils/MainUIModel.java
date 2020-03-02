package com.cleanup.todoc.ui.utils;

import java.util.Comparator;

public class MainUIModel {

    private long taskId;
    private String tasksName;
    private long creationTimestamp;
    private String projectName;
    private int color;

    public MainUIModel(long taskId, String tasksName, long creationTimestamp, String projectName, int color) {
        this.taskId = taskId;
        this.tasksName = tasksName;
        this.creationTimestamp = creationTimestamp;
        this.projectName = projectName;
        this.color = color;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getTasksName() {
        return tasksName;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getColor() {
        return color;
    }

    /**
     * Comparator to sort task from A to Z
     */
    public static class ModelAZComparator implements Comparator<MainUIModel> {
        @Override
        public int compare(MainUIModel left, MainUIModel right) {
            return left.tasksName.compareToIgnoreCase(right.tasksName);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class ModelZAComparator implements Comparator<MainUIModel> {
        @Override
        public int compare(MainUIModel left, MainUIModel right) {
            return right.tasksName.compareToIgnoreCase(left.tasksName);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class ModelRecentComparator implements Comparator<MainUIModel> {
        @Override
        public int compare(MainUIModel left, MainUIModel right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class ModelOldComparator implements Comparator<MainUIModel> {
        @Override
        public int compare(MainUIModel left, MainUIModel right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}