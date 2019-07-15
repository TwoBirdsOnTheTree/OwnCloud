import DateUtil from './DateUtil.js';

/**
 * 文件类
 * @type {MyFile}
 */
let MyFile = class {
    // 文件Id
    fileId = null;
    // 文件名
    fileName = null;
    // 文件大小
    fileSize = null;
    // 文件路径
    filePath = null;
    // 上次修改时间
    modifiedTime = null;
    // 是否是文件夹
    ifDirectory = null;

    constructor(file) {
        if (file) {
            Object.assign(this, file);
        }
    }

    // 显示上次修改时间
    showModifiedTime() {
        if (this.modifiedTime) {
            let now = new Date();

            let timeString = String(this.modifiedTime);
            if (timeString.indexOf('T') === 10) {
                timeString = timeString.replace('T', ' ');
            }
            const time = new Date(timeString);

            if (DateUtil.isSameYear(now, time)) {
                return timeString.substr(5, 11);
            }

            return timeString.substr(0, 10);
        }
    }

    // 显示大小
    showFileSize() {
        if (this.fileSize) {
            return this.fileSize;
        }
    }
};

export default MyFile;