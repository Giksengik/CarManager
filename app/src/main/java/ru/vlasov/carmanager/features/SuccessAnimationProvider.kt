package ru.vlasov.carmanager.features

import android.animation.Animator

interface SuccessAnimationProvider {
    fun provideSuccessAnimation(animatorListener : Animator.AnimatorListener)
}