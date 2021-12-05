<template>
    <div>
        <Post v-for="post in Object.values(this.posts).sort((a, b) => b.id - a.id)"
              :post="post" :comments="commentsByPostId(post.id)"
              :showComments="false" :users="users" :key="post.id">
        </Post>
    </div>
</template>

<script>
import Post from "./Post";

export default {
  name: "Index",
  components: {
    Post
  },
  props: ["posts", "users", "commentsByPostId"]
}
</script>

<style scoped>

</style>
