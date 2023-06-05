export default function checkOnlySpaces(str) {
    return str.length !== str.split(' ').length - 1;
}