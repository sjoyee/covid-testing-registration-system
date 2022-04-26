<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <div v-if="isLoggedIn">
        <v-btn text @click="backToMain">
          <v-icon left>mdi-arrow-left-bold</v-icon>
          BACK TO MAIN
        </v-btn>
      </div>
      <v-btn v-else text @click="login">LOGIN</v-btn>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: false,
    };
  },

  created() {
    this.checkLoggedIn();
  },

  methods: {
    login() {
      this.$router.push({ path: "/login" });
    },
    checkLoggedIn() {
      if (this.$route.params.id) {
        this.isLoggedIn = true;
      }
    },
    backToMain() {
      this.$router.push({
        path: `/${this.$route.params.id}/${this.$route.params.role}`,
      });
    },
  },
  computed: {},
};
</script>

<style></style>
