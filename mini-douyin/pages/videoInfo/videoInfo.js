// pages/videoInfo/videoInfo.js
var videoUtil = require("../../utils/videoUtil.js")

const app = getApp()
Page({
    data: {
        cover: "cover",
        videoId: "",
        src: "",
        videoInfo: {}
    },

    videoCtx: {},
    showSearch: function() {
        wx.navigateTo({
            url: '../searchVideo/searchVideo',
        })
    },
    onLoad: function(params) {
        var me = this;
        // 获取viedo上下文对象
        me.videoCtx = wx.createVideoContext("myVideo", me)

        // 获取上一个页面传入的参数
        var videoInfo = JSON.parse(params.videoInfo)

        // 横向视频展示优化
        var height = videoInfo.videoHeight
        var width = videoInfo.videoWidth
        var cover = "cover"
        if (width >= height) {
            cover = ""
        }

        me.setData({
            videoId: videoInfo.id,
            src: app.serverUrl + videoInfo.videoPath,
            videoInfo: videoInfo,
            cover: cover
        })
    },
    onShow: function() {
        var me = this
        me.videoCtx.play()
    },
    onHide: function() {
        var me = this
        me.videoCtx.pause()
    },
    upload: function() {
        var me = this
        var user = app.getGlobalUserInfo()
        
        // 页面重定向,并保存视频信息
        var videoInfo = JSON.stringify(me.data.videoInfo)
        // 由于会过滤到参数，所以使用#和@符号代替?和=，再在下一页面还原
        var realUrl = '../videoInfo/videoInfo#videoInfo@'+videoInfo

        if (user == null || user == undefined || user == "") {
            wx.navigateTo({
                url: '../userLogin/login?redirectUrl='+ realUrl,
            })
        } else {
            videoUtil.uploadVideo()
        }
    },

    showIndex: function() {
        wx.redirectTo({
            url: '../index/index',
        })
    },

    showMine: function() {
        var user = app.getGlobalUserInfo()

        if (user == null || user == undefined || user == "") {
            wx.navigateTo({
                url: '../userLogin/login',
            })
        } else {
            wx.navigateTo({
                url: '../mine/mine',
            })
        }
    }
})