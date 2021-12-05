<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2021 by Mike Mirzayanov
        <p>Users: {{ countUsers }}, Posts: {{ countPosts }}</p>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["countUsers", "countPosts"],
}
</script>

<style scoped>

</style>