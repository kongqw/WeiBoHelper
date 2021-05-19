# 微博开放平台封装

[![](https://jitpack.io/v/kongqw/WeiBoHelper.svg)](https://jitpack.io/#kongqw/WeiBoHelper)

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

``` gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

``` gradle
dependencies {
    implementation 'com.github.kongqw:WeiBoHelper:1.0.0'
}
```

Step 3. Add WEIBO_APP_ID in app build.gradle

``` gradle
android {
    ……
    defaultConfig {
        ……
        manifestPlaceholders = [
                WEIBO_APP_ID: "申请的微博appid"
        ]
        ……
    }
    ……
}
```

- REDIRECT_URL

    默认值为： "https://api.weibo.com/oauth2/default.html"

- SCOPE

    默认值为： "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write"

如果需要修改`REDIRECT_URL`和`SCOPE`，则在`AndroidManifest.xml`文件`application`标签下添加：

``` xml
<meta-data
    android:name="weibo_redirect_url"
    android:value="${WEIBO_REDIRECT_URL}" />

<meta-data
    android:name="weibo_scope"
    android:value="${WEIBO_SCOPE}" />
```

然后在`build.gradle`修改自定义的值

``` gradle
android {
    ……
    defaultConfig {
        ……
        manifestPlaceholders = [
                WEIBO_APP_ID      : "申请的微博appid"
                WEIBO_REDIRECT_URL: "自定义值",
                WEIBO_SCOPE       : "自定义值"
        ]
        ……
    }
    ……
}
```

## 初始化

``` kotlin
WeiBoHelper.init(applicationContext, true)
```

## 分享

### 微博授权

``` kotlin
WeiBoHelper.startAuth(mWeiBoAuthListener)
```

### 微博客户端授权

``` kotlin
WeiBoHelper.startClientAuth(mWeiBoAuthListener)
```

### 分享文字

``` kotlin
WeiBoHelper.shareText("我是分享到微博的文字！", mWeiBoShareListener)
```

### 分享单图

``` kotlin
WeiBoHelper.shareImage("https://gank.io/images/02eb8ca3297f4931ab64b7ebd7b5b89c", mWeiBoShareListener)
```

### 分享网页

``` kotlin
WeiBoHelper.shareWebpage("我是标题", "我是描述", "https://gank.io/images/02eb8ca3297f4931ab64b7ebd7b5b89c", "https://www.baidu.com", mWeiBoShareListener)
```

### TODO

> 使用较少，暂未添加，敬请期待。

- 分享多图
- 分享视频
