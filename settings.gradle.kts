dependencyResolutionManagement {
    /**
     * Central declaration of repositories
     * 不用配置allprojects或subprojects或子项目中指定，此处统一配置仓库
     */
    repositories {
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://mirrors.huaweicloud.com/repository/maven/")
        // 或者公司本地仓库
        mavenCentral()
    }
    // 默认值
    repositoriesMode = RepositoriesMode.PREFER_PROJECT
    // 如果子项目中配置了repositories，会给出警告，使用settings中的配置
    // repositoriesMode = RepositoriesMode.PREFER_SETTINGS
    // 如果子项目中配置了repositories，直接构建失败
    // repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
}

rootProject.name = "algorithm-datastructure"

