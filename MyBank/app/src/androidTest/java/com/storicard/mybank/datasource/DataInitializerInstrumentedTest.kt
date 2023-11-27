package com.storicard.mybank.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.storicard.datasource.seed.DataInitializer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class DataInitializerInstrumentedTest {

    @Test
    fun testInitializeDataIfEmpty() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dataInitializer = DataInitializer("test_data_node")

        dataInitializer.initializeDataIfEmpty()

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("test_data_node")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                assertEquals(true, dataSnapshot.exists())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Maneja errores si es necesario
            }
        })
    }
}