package com.dave.infobroadcaster.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.dave.infobroadcaster.R
import com.dave.infobroadcaster.data.entity.Article
import com.dave.infobroadcaster.data.entity.BroadcasterResponse
import com.dave.infobroadcaster.ui.theme.Purple40

@Composable
fun Loader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
            color = Purple40
        )
    }
}

@Composable
fun NewsList(response: BroadcasterResponse, page: Int) {
    val TAG = "AppComponent"
    LazyColumn {
        items(response.articles){article ->
            article.author?.let { NormalTextComponent(textValue = it) }
        }
    }
}

@Composable
fun NormalTextComponent (textValue: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Monospace
        )
    )
}

@Composable
fun HeadingTextComponent (textValue: String,centerAligned: Boolean) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        ),
        textAlign = if(centerAligned) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun NewsRowComponent (page:Int, article: Article){
    NormalTextComponent (textValue = "$page \n\n ${article. title}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            model = article. urlToImage,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error  = painterResource(id = R.drawable.ic_launcher_foreground)
        )

        Spacer(modifier = Modifier.size(20.dp))

        HeadingTextComponent(textValue = article.title?:"",false)

        Spacer(modifier = Modifier.size(10.dp))

        NormalTextComponent(textValue = article.description?:"")

        Spacer(modifier = Modifier.weight(1f))

        AuthorDetailsComponent(article.author, article.source?.name)

    }
}

@Preview(showBackground = true)
@Composable
fun NewsRowComponentPreview() {
    val article = Article(
        author = "Mr X",
        title = "Hello Dummy news article",
        null,
        null,
        null,
        null,
        null,
        null
    )
}

@Composable
fun AuthorDetailsComponent (authorName: String?, sourceName: String?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp,)

    ) {
        authorName?.also {
            //NormalTextComponent (textValue = it)
            Text(text = it)
        }

        Spacer(modifier = Modifier.size(30.dp))

        sourceName?.also {
            //NormalTextComponent(textValue = it)
            Text(text = it)
        }
    }

//        NormalTextComponent(textValue = authorName)
//        NormalTextComponent(textValue = sourceName)
    //}
}

@Composable
fun EmptyStateComponent () {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start= 10.dp, end = 10.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = androidx.transition.R.drawable.notification_bg),
            contentDescription = null
        )

        HeadingTextComponent(
            textValue = stringResource(
                R.string.nonews_as_of_now_please_check_in_some
            ),true
        )
    }

}