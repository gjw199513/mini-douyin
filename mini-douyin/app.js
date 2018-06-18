//app.js
App({
    serverUrl: "http://192.168.1.107:8080",
    userInfo: null,

    // 将userinfo设置为缓存的全局方法
    setGlobalUserInfo: function(user) {
        wx.setStorageSync("userInfo", user)
    },

    // 获取userinfo缓存的全局方法
    getGlobalUserInfo: function() {
        return wx.getStorageSync("userInfo")
    }
})