package com.example.tamagotchigame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : AppCompatActivity() {
    private var health = 100
    private var hunger = 0
    private var cleanliness = 100
    private var petState = PetState.NORMAL

    private enum class PetState {
        HUNGRY,
        PLAYING,
        CLEAN,
        NORMAL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        updatePetImage()

        feedButton.setOnClickListener {
            feedPet()
            updateStatus()
            updatePetImage()
        }

        cleanButton.setOnClickListener {
            cleanPet()
            updateStatus()
            updatePetImage()
        }

        playButton.setOnClickListener {
            playWithPet()
            updateStatus()
            updatePetImage()
        }

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    updateStatus()
                }
            }
        }, 0, 5000)
    }

    private fun feedPet() {
        hunger -= 10
        if (hunger < 0) hunger = 0
    }

    private fun cleanPet() {
        cleanliness = 100
    }

    private fun playWithPet() {
        health += 10
        if (health > 100) health = 100
    }

    private fun updateStatus() {
        if (hunger > 50 && cleanliness < 50) {
            health -= 10
        } else if (hunger > 50 || cleanliness < 50) {
            health -= 5
        }

        hunger += 5

        cleanliness -= 2

        health = health.coerceIn(0, 100)
        hunger = hunger.coerceIn(0, 100)
        cleanliness = cleanliness.coerceIn(0, 100)

        statusTextView.text = "Status: Health $health, Hunger $hunger, Cleanliness $cleanliness"
    }

    private fun updatePetImage() {
        val imageRes = when (petState) {
            PetState.HUNGRY -> R.drawable.pet_hungry
            PetState.PLAYING -> R.drawable.pet_playing
            PetState.CLEAN -> R.drawable.pet_clean
            else -> R.drawable.PetStandard
        }
        petImage.setImageResource(imageRes)
    }
}
