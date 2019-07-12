<template>
    <div>
        <el-table
                :data="myFileList"
                style="width: 100%">
            <el-table-column
                    label="名称"
                    width="180">
                <template slot-scope="scope">
                    {{scope.row.fileName}}
                </template>
            </el-table-column>
            <el-table-column
                    v-once
                    label="上次修改时间"
                    width="180">
                <template slot-scope="scope">
                    {{scope.row.showModifiedTime()}}
                </template>
            </el-table-column>
            <el-table-column
                    v-once
                    label="大小"
                    width="180">
                <template slot-scope="scope">
                    {{scope.row.showFileSize()}}
                </template>
            </el-table-column>
        </el-table>
        <!--<table>
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
            <tr v-if="null != myFileList"
                v-for="file of myFileList"
            >
                <td>{{file.fileName}}</td>
                <td @click="download(file)">下载</td>
            </tr>
        </table>
        <video ref="video" controls="controls">

        </video>-->
    </div>
</template>

<script>
    import MyFile from "../assets/js/MyFile";

    export default {
        name: "Disk",
        data() {
            return {
                myFileList: null,
                config: {
                    sortName: '',
                    sortOrder: '',
                    isShowHidden: false
                }
            }
        },

        created() {
            this.$axios.get('file/fileList?filePath=')
                .then(response => {
                    let list = null;
                    if (response && response.data) {
                        list = response.data
                            .map(m => {
                                return new MyFile(m);
                            });
                    }
                    return list;
                })
                .then(list => {
                    console.log(`list: `, list);
                    this.myFileList = list;
                });
        },

        methods: {
            download(file) {
                let filePath = file.filePath;
                // 将字符\替换为/, 否则请求报错
                filePath = String(filePath).replace(/\\/g, '/');

                // 看视频
                if (String(filePath).endsWith('.mp4')) {
                    this.$refs.video.src = 'http://localhost:8288/file/download?filePath=' + filePath;
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