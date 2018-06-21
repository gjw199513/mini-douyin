// pages/videoInfo/videoInfo.js
var videoUtil = require("../../utils/videoUtil.js")

const app = getApp()
Page({
    data: {
        cover: "cover",
        videoId: "",
        src: "",
        videoInfo: {},

        userLikeVideo: false,

        commentsPage: 1,
        commentsTotalPage: 1,
        commentsList: [],


        placeholder: "说点什么..."
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

        var serverUrl = app.serverUrl
        var user = app.getGlobalUserInfo()
        var loginUserId = ""
        if (user != null && user != undefined && user != '') {
            loginUserId = user.id
        }
        wx.request({
            url: serverUrl + '/user/queryPublisher?loginUserId=' + loginUserId + '&videoId=' + videoInfo.id + '&publishUserId=' + videoInfo.userId,
            method: 'POST',
            success: function(res) {
                console.log(res.data)

                var publisher = res.data.data.publisher
                var userLikeVideo = res.data.data.userLikeVideo

                me.setData({
                    serverUrl: serverUrl,
                    publisher: publisher,
                    userLikeVideo: userLikeVideo,
                })
            }
        })

        me.getCommentsList(1)

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
        var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo

        if (user == null || user == undefined || user == "") {
            wx.navigateTo({
                url: '../userLogin/login?redirectUrl=' + realUrl,
            })
        } else {
            videoUtil.uploadVideo()
        }
    },
    showPublisher: function() {
        var me = this
        var user = app.getGlobalUserInfo()

        var videoInfo = me.data.videoInfo
        var realUrl = '../mine/mine#publisher@' + videoInfo.userId

        if (user == null || user == undefined || user == "") {
            wx.navigateTo({
                url: '../userLogin/login?redirectUrl=' + realUrl,
            })
        } else {
            wx.navigateTo({
                url: '../mine/mine?publisherId=' + videoInfo.userId,
            })
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
    },
    likeVideoOrNot: function() {
        var me = this
        var user = app.getGlobalUserInfo()
        var videoInfo = me.data.videoInfo

        if (user == null || user == undefined || user == "") {
            wx.navigateTo({
                url: '../userLogin/login',
            })
        } else {
            var userLikeVideo = me.data.userLikeVideo
            var url = '/video/userLike?userId=' + user.id + '&videoId=' + videoInfo.id + '&videoCreaterId=' + videoInfo.userId
            if (userLikeVideo) {
                var url = '/video/userUnLike?userId=' + user.id + '&videoId=' + videoInfo.id + '&videoCreaterId=' + videoInfo.userId
            }

            var serverUrl = app.serverUrl
            wx.showLoading({
                title: '...',
            })
            wx.request({
                url: serverUrl + url,
                method: "POST",
                header: {
                    'content-type': 'application/json', // 默认值
                    'userId': user.id,
                    'userToken': user.userToken
                },
                success: function(res) {
                    wx.hideLoading()
                    me.setData({
                        userLikeVideo: !userLikeVideo
                    })
                }
            })
        }
    },
    shareMe: function() {
        var me = this;
        var user = app.getGlobalUserInfo();

        wx.showActionSheet({
            itemList: ['下载到本地', '举报用户', '分享到朋友圈', '分享到QQ空间', '分享到微博'],
            success: function(res) {
                console.log(res.tapIndex);
                if (res.tapIndex == 0) {
                    // 下载
                    wx.showLoading({
                        title: '下载中...',
                    })
                    wx.downloadFile({
                        url: app.serverUrl + me.data.videoInfo.videoPath,
                        success: function(res) {
                            // 只要服务器有响应数据，就会把响应内容写入文件并进入 success 回调，业务需要自行判断是否下载到了想要的内容
                            if (res.statusCode === 200) {
                                console.log(res.tempFilePath);

                                wx.saveVideoToPhotosAlbum({
                                    filePath: res.tempFilePath,
                                    success: function(res) {
                                        console.log(res.errMsg)
                                        wx.hideLoading();
                                    }
                                })
                            }
                        }
                    })
                } else if (res.tapIndex == 1) {
                    // 举报
                    var videoInfo = JSON.stringify(me.data.videoInfo);
                    var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo;

                    if (user == null || user == undefined || user == '') {
                        wx.navigateTo({
                            url: '../userLogin/login?redirectUrl=' + realUrl,
                        })
                    } else {
                        var publishUserId = me.data.videoInfo.userId;
                        var videoId = me.data.videoInfo.id;
                        var currentUserId = user.id;
                        wx.navigateTo({
                            url: '../report/report?videoId=' + videoId + "&publishUserId=" + publishUserId
                        })
                    }
                } else {
                    wx.showToast({
                        title: '官方暂未开放...',
                    })
                }
            }
        })
    },

    onShareAppMessage: function(res) {
        var me = this

        var videoInfo = me.data.videoInfo
        return {
            title: '迷你抖音分享',
            path: '../videoInfo/videoInfo?videoInfo=' + JSON.stringify(videoInfo)
        }
    },


    leaveComment: function () {
        this.setData({
            commentFocus: "commentFocus"
        })
    },

    replyFocus: function (e) {

        var fatherCommentId = e.currentTarget.dataset.fathercommentid
        var toUserId = e.currentTarget.dataset.touserid
        var toNickname = e.currentTarget.dataset.tonickname

        this.setData({
            placeholder: "回复 " + toNickname,
            // 回复时带入的参数
            replyFatherCommentId: fatherCommentId,
            replyToUserId: toUserId,
            commentFocus: true
        })
    },

    saveComment: function (e) {
        var me = this
        var content = e.detail.value

        // 获取评论回复的fatherCommentId和toUserId
        var fatherCommentId = e.currentTarget.dataset.replyfathercommentid
        var toUserId = e.currentTarget.dataset.replytouserid

        var user = app.getGlobalUserInfo();
        var videoInfo = JSON.stringify(me.data.videoInfo);
        var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo;

        if (user == null || user == undefined || user == '') {
            wx.navigateTo({
                url: '../userLogin/login?redirectUrl=' + realUrl,
            })
        } else {
            wx.showLoading({
                title: '请稍后...',
            })
            var serverUrl = app.serverUrl
            wx.request({
                url: serverUrl + '/video/saveComment?fatherCommentId=' + fatherCommentId + "&toUserId=" + toUserId,
                method: "POST",
                header: {
                    'content-type': 'application/json', // 默认值
                    'userId': user.id,
                    'userToken': user.userToken
                },
                data: {
                    fromUserId: user.id,
                    videoId: me.data.videoInfo.id,
                    comment: content
                },
                success: function (res) {
                    console.log(res.data)
                    wx.hideLoading()

                    // 将值重置为空
                    me.setData({
                        contentValue: "",
                        commentsList: []
                    })

                    me.getCommentsList(1)
                }
            })
        }
    },
    // commentsPage: 1,
    // commentsTotalPage: 1,
    // commentsList: [],
    getCommentsList: function (page) {
        var me = this

        var videoId = me.data.videoInfo.id

        wx.request({
            url: app.serverUrl + '/video/getVideoComments?videoId=' + videoId + "&page=" + page + "&pageSize=5",
            method: "POST",
            success: function (res) {
                console.log(res.data)

                var commentsList = res.data.data.rows
                var newCommentList = me.data.commentsList

                me.setData({
                    commentsList: newCommentList.concat(commentsList),
                    commentsPage: page,
                    commentsTotalPage: res.data.data.total,
                })
            }
        })
    },

    onReachBottom: function () {
        var me = this
        var currentPage = me.data.commentsPage
        var totalPage = me.data.commentsTotalPage
        if (currentPage === totalPage) {
            return
        }
        var page = currentPage + 1
        me.getCommentsList(page)

    }
})