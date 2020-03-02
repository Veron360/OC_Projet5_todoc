package com.cleanup.todoc;

import com.cleanup.todoc.ui.utils.MainUIModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {
    @Test
    public void test_az_comparator() {
        final MainUIModel task1 = new MainUIModel(1, "aaa", 123, "Test Project", 0xFFEADAD1);
        final MainUIModel task2 = new MainUIModel(2, "zzz", 124, "Test Project", 0xFFEADAD1);
        final MainUIModel task3 = new MainUIModel(3, "hhh", 125, "Test Project", 0xFFEADAD1);

        final ArrayList<MainUIModel> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new MainUIModel.ModelAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final MainUIModel task1 = new MainUIModel(1, "aaa", 123, "Test Project", 0xFFEADAD1);
        final MainUIModel task2 = new MainUIModel(2, "zzz", 124, "Test Project", 0xFFEADAD1);
        final MainUIModel task3 = new MainUIModel(3, "hhh", 125, "Test Project", 0xFFEADAD1);

        final ArrayList<MainUIModel> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new MainUIModel.ModelZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final MainUIModel task1 = new MainUIModel(1, "aaa", 123, "Test Project", 0xFFEADAD1);
        final MainUIModel task2 = new MainUIModel(2, "zzz", 124, "Test Project", 0xFFEADAD1);
        final MainUIModel task3 = new MainUIModel(3, "hhh", 125, "Test Project", 0xFFEADAD1);

        final ArrayList<MainUIModel> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new MainUIModel.ModelRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final MainUIModel task1 = new MainUIModel(1, "aaa", 123, "Test Project", 0xFFEADAD1);
        final MainUIModel task2 = new MainUIModel(2, "zzz", 124, "Test Project", 0xFFEADAD1);
        final MainUIModel task3 = new MainUIModel(3, "hhh", 125, "Test Project", 0xFFEADAD1);

        final ArrayList<MainUIModel> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new MainUIModel.ModelOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}
