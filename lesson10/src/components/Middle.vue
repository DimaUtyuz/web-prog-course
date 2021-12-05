<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts" :users="users" :commentsByPostId="commentsByPostId"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <Post v-if="page === 'Post'" :post="post" :comments="commentsByPostId(post.id)" :users="users" :showComments="true"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "@/components/page/Register";
import Users from "@/components/page/Users";
import Post from "@/components/page/Post";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: undefined
        }
    },
    components: {
        WritePost,
        Enter,
        Register,
        Index,
        Users,
        Post,
        Sidebar,
        EditPost
    },
    props: ["posts", "users", "comments"],
    methods: {
        commentsByPostId: function (postId) {
            return Object.values(this.comments).filter(c => c.postId === postId)
        }
    },
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
        this.$root.$on("onShowPost", (post) => {this.page = "Post"; this.post = post})
    }
}
</script>

<style scoped>

</style>
