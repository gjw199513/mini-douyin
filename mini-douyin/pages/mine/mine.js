var videoUtil = require('../../utils/videoUtil.js')

const app = getApp()

Page({
    data: {
        faceUrl: "../resource/images/noneface.png",


    },

    onLoad: function(params) {
        var me = this
        // var user = app.userInfo
        // fixme 修改原有的全局对象为本地缓存
        var user = app.getGlobalUserInfo()

        var serverUrl = app.serverUrl;

        wx.showLoading({
            title: '请等待...',
        });

        wx.request({
            url: serverUrl + '/user/query?userId=' + user.id,
            method: "POST",
            header: {
                'content-type': 'application/json' // 默认值
            },
            success: function(res) {
                console.log(res.data);
                //   在成功后隐藏等待
                wx.hideLoading();
                if (res.data.status == 200) {
                    var userInfo = res.data.data;
                    var faceUrl = "../resource/images/noneface.png"
                    // 判断是否有头像
                    if (userInfo.faceImage != null && userInfo.faceImage != "" && userInfo.faceImage != undefined) {
                        faceUrl = serverUrl + userInfo.faceImage;
                    }
                    me.setData({
                        faceUrl: faceUrl,
                        fansCounts: userInfo.fansCounts,
                        followCounts: userInfo.followCounts,
                        receiveLikeCounts: userInfo.receiveLikeCounts,
                        nickname: userInfo.nickname
                    })
                }
            }
        })
    },

    logout: function() {
        // var user = app.userInfo;
        var user = app.getGlobalUserInfo()        
        var serverUrl = app.serverUrl;
        wx.showLoading({
            title: '请等待...',
        });
        // 调用后端
        wx.request({
            url: serverUrl + '/logout?userId=' + user.id,
            method: "POST",
            header: {
                'content-type': 'application/json' // 默认值
            },
            success: function(res) {
                console.log(res.data);
                //   在成功后隐藏等待
                wx.hideLoading();
                if (res.data.status == 200) {
                    wx.showToast({
                        title: '注销成功',
                        icon: 'success',
                        duration: 2000
                    });
                    // app.userInfo = null;
                    // 注销以后，清空缓存
                    wx.removeStorageSync("userInfo")
                    wx.navigateTo({
                        url: '../userLogin/login',
                    })
                }
            }
        })
    },
    changeFace: function() {
        var me = this
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function(res) {
                // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
                var tempFilePaths = res.tempFilePaths
                console.log(tempFilePaths)

                wx.showLoading({
                    title: '上传中...',
                })
                var serverUrl = app.serverUrl
                // fixme 修改原有的全局对象为本地缓存
                var userInfo = app.getGlobalUserInfo()
                // 上传头像
                wx.uploadFile({
                    url: serverUrl + '/user/uploadFace?userId=' + userInfo.id,  // app.userInfo.id
                    filePath: tempFilePaths[0],
                    name: 'file',
                    header: {
                        'content-type': 'application/json' // 默认值
                    },
                    success: function(res) {
                        // 由于uploadFile返回的Stirng类型，需要格式化成json对象
                        var data = JSON.parse(res.data)
                        console.log(data)
                        wx.hideLoading()
                        if (data.status == 200) {
                            wx.showToast({
                                title: '上传成功~~',
                                icon: 'success'
                            })

                            var imageUrl = data.data;
                            me.setData({
                                faceUrl: serverUrl + imageUrl
                            })
                        } else if (data.status == 500) {
                            wx.showToast({
                                title: data.msg,
                            })
                        }
                    }
                })
            }
        })
    },
    uploadVideo: function() {
        // videoUtil.uploadVideo()
        
        var me = this
        wx.chooseVideo({
            sourceType: ['album', 'camera'],
            maxDuration: 60,
            camera: 'back',
            success: function(res) {
                console.log(res)
                var duration = res.duration
                var tmpHeight = res.height
                var tmpWidth = res.width
                var tmpVideoUrl = res.tempFilePath
                var tmpCoverUrl = res.thumbTempFilePath

                if (duration > 11) {
                    wx.showToast({
                        title: '视频长度不能超过10秒...',
                        icon: 'none',
                        duration: 2500
                    })
                } else if (duration < 1) {
                    wx.showToast({
                        title: '视频长度太短，请上传超过1秒的视频...',
                        icon: 'none',
                        duration: 2500
                    })
                } else {
                    // 打开选择bgm的页面
                    wx.navigateTo({
                        url: '../chooseBgm/chooseBgm?duration=' + duration +
                            "&tmpHeight=" + tmpHeight +
                            "&tmpWidth=" + tmpWidth +
                            "&tmpVideoUrl=" + tmpVideoUrl +
                            "&tmpCoverUrl=" + tmpCoverUrl
                    })
                }
            }
        })
    }
})