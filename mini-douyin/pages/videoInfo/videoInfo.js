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
        me.setData({
            videoId: videoInfo.id,
            src: app.serverUrl + videoInfo.videoPath,
            videoInfo: videoInfo
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
        videoUtil.uploadVideo()
    },

    showIndex: function() {
        wx.redirectTo({
            url: '../index/index',
        })
    }

})