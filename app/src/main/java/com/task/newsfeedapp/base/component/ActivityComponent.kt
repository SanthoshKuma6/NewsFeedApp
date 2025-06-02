package com.task.newsfeedapp.base.component

import com.task.newsfeedapp.activity.MainActivity
import com.task.newsfeedapp.base.module.ActivityModule
import com.task.newsfeedapp.base.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
    fun inject(launchActivity: MainActivity)
}
