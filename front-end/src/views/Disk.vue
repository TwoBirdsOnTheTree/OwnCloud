<template>
    <div>
        {{test}}
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
                    test = response;
                    console.log(response);
                });
        }
    }
</script>

<style scoped>

</style>