// pages/videoInfo/videoInfo.js
var videoUtil = require("../../utils/videoUtil.js")

const app = getApp()
Page({
    data: {
        cover: "cover"
    },

    videoCtx: {},
    showSearch: function() {
        wx.navigateTo({
            url: '../searchVideo/searchVideo',
        })
    },
    onLoad: function(options) {
        var me = this;
        // 获取viedo上下文对象
        me.videoCtx = wx.createVideoContext("myVideo", me)
    },
    onShow: function() {
    var me = this
    me.videoCtx.play()
},
onHide: function(){
    var me = this
    me.videoCtx.pause()
},
upload: function(){
    videoUtil.uploadVideo()
}

})