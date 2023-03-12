package com.grayseal.safecity.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grayseal.safecity.R
import com.grayseal.safecity.ui.theme.Green
import com.grayseal.safecity.ui.theme.poppinsFamily
import java.text.SimpleDateFormat
import java.util.*

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrimeReportForm() {
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var crimeDescription by remember { mutableStateOf("") }
    var victimName by remember { mutableStateOf("") }
    var victimID by remember { mutableStateOf("") }
    var victimContact by remember { mutableStateOf("") }
    var suspectName by remember { mutableStateOf("") }
    var suspectDescription by remember { mutableStateOf("") }
    var witnessName by remember { mutableStateOf("") }
    var witnessContact by remember { mutableStateOf("") }
    var witnessDescription by remember { mutableStateOf("") }
    var evidence by remember { mutableStateOf("") }
    var otherInformation by remember { mutableStateOf("") }

    // Types
    val crimes = listOf("Theft", "Burglary", "Assault", "Vandalism", "Fraud")
    var crimeExpanded by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // CALENDAR
    val cal = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
            date = selectedDate // set the selected date to the mutableStateOf variable
            showCalendar = false
        },
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            "Report a crime",
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        // Incident details
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(
                    "Please provide the details of the incident, " +
                            "including the date and time it occurred, as well as the " +
                            "specific location where it took place.",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = poppinsFamily,
                    fontSize = 13.sp
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    placeholder = {
                        Text(
                            "Time of Crime",
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    placeholder = { Text("Date of Crime") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            showCalendar = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar),
                                "Drop down menu",
                                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        }
                    },
                )
                if (showCalendar) {
                    datePickerDialog.show()
                }
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = { Text("Location of Crime") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
            }
        }

        // Crime description
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(
                    "Please provide a brief description of the type of crime that " +
                            "occurred, such as theft, burglary, or assault",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = poppinsFamily,
                    fontSize = 13.sp
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = crimeDescription,
                        onValueChange = { crimeDescription = it },
                        placeholder = { Text("Crime description") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Green,
                            focusedBorderColor = Green
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                crimeExpanded = true
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow),
                                    "Drop down menu",
                                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                )
                            }
                        },
                        readOnly = true,
                    )
                    if (crimeExpanded) {
                        DropdownMenu(
                            expanded = true,
                            onDismissRequest = { },
                            modifier = Modifier.background(color = Color.White)
                        ) {
                            crimes.forEach { crime ->
                                DropdownMenuItem(
                                    text = {
                                        Text(text = crime)
                                    },
                                    onClick = {
                                        crimeDescription = crime
                                        crimeExpanded = false
                                    },
                                    modifier = Modifier.widthIn(max = 300.dp),
                                    interactionSource = MutableInteractionSource()
                                )
                            }
                        }
                    }
                }
            }
        }
        // Victim information
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(
                    "Please provide the victim's name, ID number (if available), and " +
                            "contact information (such as phone number or email address).",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = poppinsFamily,
                    fontSize = 13.sp
                )
                OutlinedTextField(
                    value = victimID,
                    onValueChange = { victimID = it },
                    placeholder = { Text("Victim National ID no.") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
                OutlinedTextField(
                    value = victimName,
                    onValueChange = { victimName = it },
                    placeholder = { Text("Victim name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
                OutlinedTextField(
                    value = victimContact,
                    onValueChange = { victimContact = it },
                    placeholder = { Text("Victim contact information") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
            }
        }
        // Suspect information
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(
                    "Please provide any details you have about the suspect(s), including " +
                            "their physical description, name (if known), and any identifying " +
                            "features such as tattoos or scars.",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = poppinsFamily,
                    fontSize = 13.sp
                )
                OutlinedTextField(
                    value = suspectName,
                    onValueChange = { suspectName = it },
                    placeholder = { Text("Suspect name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
                OutlinedTextField(
                    value = suspectDescription,
                    onValueChange = { suspectDescription = it },
                    placeholder = { Text("Suspect description") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
            }
        }

        // Witness information
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(
                    "Please provide the name(s) and contact information (such as phone " +
                            "number or email address) of any witnesses to the crime. " +
                            "Additionally, you may provide a brief description of what the witness(es) saw.",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = poppinsFamily,
                    fontSize = 13.sp
                )
                OutlinedTextField(
                    value = witnessName,
                    onValueChange = { witnessName = it },
                    placeholder = { Text("Witness name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
                OutlinedTextField(
                    value = witnessContact,
                    onValueChange = { witnessContact = it },
                    placeholder = { Text("Witness contact information") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
                OutlinedTextField(
                    value = witnessDescription,
                    onValueChange = { witnessDescription = it },
                    placeholder = { Text("Brief description") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
            }
        }

        // Evidence
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(
                    "Please provide any evidence or additional information related to the " +
                            "crime. This could include photos, videos, or any other relevant documents.",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = poppinsFamily,
                    fontSize = 13.sp
                )
                OutlinedTextField(
                    value = evidence,
                    onValueChange = { evidence = it },
                    placeholder = { Text("Evidence") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
            }
        }

        // Other information
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(
                    "If you have any other information related to the crime that you " +
                            "think may be helpful, please provide it below.",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = poppinsFamily,
                    fontSize = 13.sp
                )
                OutlinedTextField(
                    value = otherInformation,
                    onValueChange = { otherInformation = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Green,
                        focusedBorderColor = Green
                    )
                )
            }
        }
        // Submit button
        androidx.compose.material3.Button(
            onClick = { submitReport() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green)
        ) {
            Text(
                "REPORT",
                fontFamily = poppinsFamily,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

fun submitReport() {
    // TODO: Implement the logic for submitting the crime report
}
