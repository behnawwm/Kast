package com.example.kast.android.ui.shared_components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*

@Composable
fun ExpandingText(
    text: String,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    showMoreText: String = "Show More",
    showLessText: String = "Show Less",
    isShowLessEnabled: Boolean = true,
    labelTextColor: Color = Color.Unspecified,
    minimizedLines: Int = 3,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(AnnotatedString.Builder(text).toAnnotatedString()) }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                val showLessString = " ... $showLessText"
                finalText = buildAnnotatedString {
                    append(text)
                    withStyle(style = SpanStyle(color = labelTextColor)) {
                        if (isShowLessEnabled)
                            append(showLessString)
                    }
                }
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(minimizedLines - 1)
                val showMoreString = " ... $showMoreText"
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                finalText = buildAnnotatedString {
                    append(adjustedText)
                    withStyle(style = SpanStyle(color = labelTextColor)) {
                        append(showMoreString)
                    }
                }
                isClickable = true
            }
        }
    }

    Text(
        text = finalText,
        maxLines = if (isExpanded) Int.MAX_VALUE else minimizedLines,
        onTextLayout = { textLayoutResultState.value = it },
        color = color,
        style = style,
        modifier = modifier
            .clickable(enabled = isClickable) {
                isExpanded = if (isShowLessEnabled)
                    !isExpanded
                else {
                    isClickable = false
                    true
                }
            }
            .animateContentSize(),
    )
}