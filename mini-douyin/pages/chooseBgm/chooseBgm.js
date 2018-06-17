const app = getApp()

Page({
    bgmList: [],
    serverUrl: "",
    videoParams: {},

    onLoad: function(params) {
        var me = this
        console.log(params)
        var user = app.userInfo
        var serverUrl = app.serverUrl;
        me.setData({
            videoParams: params
        })

        wx.showLoading({
            title: '请等待...',
        });

        wx.request({
            url: serverUrl + '/bgm/list',
            method: "POST",
            header: {
                'content-type': 'application/json' // 默认值
            },
            success: function(res) {
                console.log(res.data);
                //   在成功后隐藏等待
                wx.hideLoading();
                if (res.data.status == 200) {
                    var bgmList = res.data.data
                    me.setData({
                        bgmList: bgmList,
                        serverUrl: serverUrl
                    })
                }
            }
        })

    },

    upload: function(e) {
        var me = this
        var bgmId = e.detail.value.bgmId
        var desc = e.detail.value.desc

        console.log("bgmId", bgmId)
        console.log("desc", desc)

        // 获取video属性
        var duration = me.data.videoParams.duration
        var tmpHeight = me.data.videoParams.tmpHeight
        var tmpWidth = me.data.videoParams.tmpWidth
        var tmpVideoUrl = me.data.videoParams.tmpVideoUrl
        var tmpCoverUrl = me.data.videoParams.tmpCoverUrl

        // 上传短视频
        wx.showLoading({
            title: '上传中...',
        })
        var serverUrl = app.serverUrl

        wx.uploadFile({
            url: serverUrl + '/video/upload',
            // 将传入参数作为对象传递
            formData: {
                userId: app.userInfo.id,
                bgmId: bgmId,
                desc: desc,
                videoSeconds: duration,
                videoHeight: tmpHeight,
                videoWidth: tmpWidth
            },
            filePath: tmpVideoUrl,
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
                    // var videoId = data.data
                    // debugger
                    // wx.showLoading({
                    //     title: '上传中...',
                    // })
                    // wx.uploadFile({
                    //     url: serverUrl + '/video/uploadCover',
                    //     // 将传入参数作为对象传递
                    //     formData: {
                    //         userId: app.userInfo.id,
                    //         videoId: videoId,
                    //     },
                    //     filePath: tmpCoverUrl,
                    //     name: 'file',
                    //     header: {
                    //         'content-type': 'application/json' // 默认值
                    //     },
                    //     success: function(res) {
                    //         // 由于uploadFile返回的Stirng类型，需要格式化成json对象
                    //         var data = JSON.parse(res.data)
                    //         wx.hideLoading()
                    //         if (data.status == 200) {
                    //             wx.showToast({
                    //                 title: '上传成功~~',
                    //                 icon: 'success'
                    //             });
                    wx.navigateBack({
                        delta: 1
                    })

                } else {
                    wx.showToast({
                        title: '上传失败~~',
                        icon: 'success'
                    })
                }
            }
        })
    }
})