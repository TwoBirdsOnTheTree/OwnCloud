<template>
    <div>
        <table>
            <thead>
            <tr>
                <th>
                    <div>文件名</div>
                </th>
                <th>
                    <div>操作</div>
                </th>
            </tr>
            </thead>
            <tr v-if="null != test"
                v-for="file of test"
            >
                <td>{{file.fileName}}</td>
                <td @click="download(file)">下载</td>
            </tr>
        </table>
        <video ref="video" controls="controls">

        </video>
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
            this.$axios.get('file/fileList?filePath=')
                .then((response) => {
                    this.test = response.data;
                });
        },
        methods: {
            download(file) {
                let filePath = file.filePath;
                // 将字符\替换为/, 否则请求报错
                filePath = String(filePath).replace(/\\/g, '/');

                // 看视频
                if (String(filePath).endsWith('.mp4')) {
                    this.$refs.video.src = 'http://localhost:8288/file/download?filePath=' + filePath
                    return;
                }

                // 下载
                console.log(file);
                window.open('http://localhost:8288/file/download?filePath=' + filePath);
            }
        }
    }
</script>

<style scoped>

</style>