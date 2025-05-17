package com.task.newsfeedapp.base.component

import com.google.android.datatransport.runtime.dagger.Component
import com.task.newsfeedapp.activity.MainActivity
import com.task.newsfeedapp.base.module.ActivityModule
import com.task.newsfeedapp.base.scope.ActivityScope

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
    fun inject(launchActivity: MainActivity)
}
