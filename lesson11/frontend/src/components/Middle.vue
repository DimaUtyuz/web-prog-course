<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'" :posts="posts"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <Users v-if="page === 'Users'" :users="users"/>
      <WritePost v-if="page === 'WritePost'"/>
      <Post v-if="page === 'Post'" :post="post" :showComments="true"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "./main/Users";
import WritePost from "@/components/main/WritePost";
import Post from "@/components/main/Post";

export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index",
      post: null
    }
  },
  components: {
    Post,
    WritePost,
    Register,
    Enter,
    Index,
    Sidebar,
    Users
  },
  props: ["posts", "users", "user"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    }
  }, beforeCreate() {
    this.$root.$on("onChangePage", (page) => this.page = page);
    this.$root.$on("onShowPost", (post) => {this.page = "Post"; this.post = post});
  }
}
</script>

<style scoped>

</style>
