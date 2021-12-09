<template>
    <div>
        <Post v-for="post in sortPost()"
              :post="post" :comments="commentsByPostId(post.id)" :showComments="false" :key="post.id">
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
  methods: {
    sortPost: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id);
    }
  },
  props: ["posts", "commentsByPostId"]
}
</script>

<style scoped>

</style>
