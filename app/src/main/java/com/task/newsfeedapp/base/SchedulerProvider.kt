package com.task.newsfeedapp.base

import com.google.android.datatransport.runtime.scheduling.Scheduler
import javax.inject.Singleton

@Singleton
interface SchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}