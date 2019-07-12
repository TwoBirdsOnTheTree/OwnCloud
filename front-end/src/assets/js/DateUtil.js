/**
 * 日期工具
 */
let util = {
    // 是否是相同的年
    isSameYear(dateA, dateB) {
        if (!dateA && !dateB) {
            return true;
        }
        if (dateA && !dateB) {
            return false;
        }
        if (!dateA && dateB) {
            return false;
        }
        return dateA.getFullYear() === dateB.getFullYear();
    },

    // 格式化
    format(date) {

    }
};

export default util;