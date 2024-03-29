package com.thkj.liveeventbus.androidx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Administrator
 * @Name ExternalLiveData
 * @Description //TODO
 * @Date 2024-03-29 11:28
 */
open class ExternalLiveData<T> : MutableLiveData<T>() {

    @Override
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // 验证生命周期状态
        if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }
        // 创建自定义观察者包装器
        val wrapperObserver = ExternalLifecycleBoundObserver(owner, observer)

        // 使用super.observe()方法，它内部会处理观察者集合的管理
        super.observe(owner, wrapperObserver)
    }

    /**
     * 自定义观察者包装器，根据observerActiveLevel()方法决定何时激活观察者
     */
    inner class ExternalLifecycleBoundObserver(
        private val owner: LifecycleOwner,
        private val delegateObserver: Observer<in T>
    ) : Observer<T> {

        override fun onChanged(value: T) {
            if (owner.lifecycle.currentState.isAtLeast(observerActiveLevel())) {
                delegateObserver.onChanged(value)
            }
        }
    }

    /**
     * 确定观察者何时变为活动状态，即可以接收消息
     */
    protected open fun observerActiveLevel(): Lifecycle.State = Lifecycle.State.CREATED

}
