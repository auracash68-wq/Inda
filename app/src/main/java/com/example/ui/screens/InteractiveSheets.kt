package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActiveSheet
import com.example.ui.theme.CardBorderColor
import com.example.ui.theme.CashIndiaFontFamily
import com.example.ui.theme.GreenButtonGradient
import com.example.ui.theme.OrangeButtonGradient
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashIndiaInteractiveSheet(
    sheet: ActiveSheet,
    selectedLanguage: String,
    onSelectLanguage: (String) -> Unit,
    onSubmitTicket: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = PureWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .padding(bottom = 24.dp)
        ) {
            when (sheet) {
                is ActiveSheet.OfferLaunch -> {
                    Text(
                        text = sheet.title,
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Provider: ${sheet.provider} • Reward: ${sheet.rewardText}",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = PrimaryGreen
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = sheet.instructions,
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = SecondaryText
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(brush = OrangeButtonGradient)
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Continue to Offer",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = PureWhite
                        )
                    }
                }

                ActiveSheet.LanguagePicker -> {
                    Text(
                        text = "Select App Language",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    val languages = listOf("English", "বাংলা (Bengali)", "हिंदी (Hindi)")
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        languages.forEach { lang ->
                            val isSelected = lang.startsWith(selectedLanguage)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) PrimaryGreen else CardBorderColor,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable { onSelectLanguage(lang.substringBefore(" ")) }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = lang,
                                    fontFamily = CashIndiaFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = PrimaryNavy
                                )
                                if (isSelected) {
                                    Text(
                                        text = "Active",
                                        fontFamily = CashIndiaFontFamily,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 13.sp,
                                        color = PrimaryGreen
                                    )
                                }
                            }
                        }
                    }
                }

                ActiveSheet.SecurityVpnPolicy -> {
                    Text(
                        text = "Security & VPN Policy",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Keep VPN, proxy, and ad-blockers turned off while completing surveys, game offers, and app tasks.\n" +
                            "• Only one account per device and verified UPI ID is permitted.\n" +
                            "• Coins and rupee rewards are credited after official partner callback verification.",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = SecondaryText
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .clip(RoundedCornerShape(21.dp))
                            .background(brush = GreenButtonGradient)
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Understood",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = PureWhite
                        )
                    }
                }

                ActiveSheet.MissingRewardTicket -> {
                    var issueDescription by remember { mutableStateOf("") }
                    Text(
                        text = "Missing Reward Ticket",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enter the task or survey name and completion time for provider verification:",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.5.sp,
                        color = SecondaryText
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = issueDescription,
                        onValueChange = { issueDescription = it },
                        placeholder = { Text("e.g., Paytm Money Sign-Up completed at 2:10 PM") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .clip(RoundedCornerShape(21.dp))
                            .background(brush = OrangeButtonGradient)
                            .clickable { onSubmitTicket(issueDescription) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Submit Ticket",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = PureWhite
                        )
                    }
                }

                ActiveSheet.ChatSupport -> {
                    Text(
                        text = "24/7 Chat Support",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Support Desk Status: Online • Average response time under 5 minutes for UPI payout & UTR inquiries.",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = SecondaryText
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .clip(RoundedCornerShape(21.dp))
                            .background(brush = GreenButtonGradient)
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Close Support Window",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = PureWhite
                        )
                    }
                }

                ActiveSheet.TermsAndPrivacy -> {
                    Text(
                        text = "Terms of Service & Privacy Policy",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cash India v1.0.0 processes task rewards upon verified partner completion. Personal UPI identifiers are used solely for requested payouts.",
                        fontFamily = CashIndiaFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = SecondaryText
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .clip(RoundedCornerShape(21.dp))
                            .background(PrimaryNavy)
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Close",
                            fontFamily = CashIndiaFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = PureWhite
                        )
                    }
                }
            }
        }
    }
}
