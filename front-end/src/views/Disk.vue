<template>
    <div>
        <table>
            <tr>
                <td>
                    文件名
                </td>
                <td>操作</td>
            </tr>
            <tr v-if="null != test"
                v-for="file of test"
            >
                <td>{{file.fileName}}</td>
                <td @click="download(file)">下载</td>
            </tr>
        </table>
    </div>
</template>

<script>
    export default {
        name: "Disk",
        data() {
            return {
                test: null
            }
        },
        created() {
            this.$axios.get('file/fileList?filePath=chat')
                .then((response) => {
                    this.test = response.data;
                });
        },
        methods: {
            download(file) {
                console.log(file);
                let filePath = file.filePath;
                // 将字符\替换为/, 否则请求报错
                filePath = String(filePath).replace(/\\/g, '/');
                window.open('http://localhost:8188/file/download?filePath=' + filePath);
            }
        }
    }
</script>

<style scoped>

</style>