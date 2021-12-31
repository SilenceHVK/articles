package handle

import (
	"github.com/SilenceHVK/blog/golang/gin/proto-source/proto"
	"io/ioutil"
	"net/http"

	"github.com/SilenceHVK/blog/golang/gin/dto"

	"github.com/gin-gonic/gin"
)

func Get(context *gin.Context) {
	context.JSON(http.StatusOK, map[string]interface{}{
		"message": "pong",
	})
}

func Post(context *gin.Context) {
	context.JSON(http.StatusOK, gin.H{
		"message": "post",
	})
}

func Delete(context *gin.Context) {
	context.JSON(http.StatusOK, gin.H{
		"message": "delete",
	})
}

func Any(context *gin.Context) {
	context.JSON(http.StatusOK, gin.H{
		"message": "any",
	})
}

func GetName(context *gin.Context) {
	context.String(http.StatusOK, "Hello "+context.Param("name"))
}

func GetQuery(context *gin.Context) {
	context.String(http.StatusOK, "Query "+context.DefaultQuery("name", "test"))
}

func FormMap(context *gin.Context) {
	formMap := context.PostFormMap("user")
	context.JSON(http.StatusOK, gin.H{
		"name": formMap["name"],
		"age":  formMap["age"],
	})
}

func PostBody(context *gin.Context) {
	// 获取 Post Form 提交数据
	content, err := ioutil.ReadAll(context.Request.Body)
	if err != nil {
		context.String(http.StatusBadRequest, err.Error())
		context.Abort() // 直接让输出结束
	}
	context.String(http.StatusOK, string(content))
}

func BindStruct(context *gin.Context) {
	var user dto.User
	// 根据不同的 Content-Type 做不同的 binding 操作
	if err := context.ShouldBind(&user); err == nil {
		context.String(http.StatusOK, "%v", user)
	} else {
		context.String(http.StatusBadRequest, "%v", err)
	}
}

// RenderTemplate 渲染模板
func RenderTemplate(context *gin.Context) {
	context.HTML(http.StatusOK, "index.html", gin.H{
		"title": "Render Template",
	})
}

// Protobuf 返回 protobuf
func Protobuf(context *gin.Context) {
	teacher := &proto.Teacher{
		Name:   "hvkcoder",
		Course: []string{"Golang", "Java", "Python", "JavaScript"},
	}
	context.ProtoBuf(http.StatusOK, teacher)
}

// PureJson json 会将特殊的HTML字符替换为对应的 unicode 字符 pureJSON 则会原样返回
func PureJson(context *gin.Context) {
	context.PureJSON(http.StatusOK, gin.H{
		"html": "<h1>Hello Pure</h1>",
	})
}
