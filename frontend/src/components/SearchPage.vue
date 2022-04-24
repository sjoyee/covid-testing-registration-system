<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="login">LOGIN</v-btn>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  methods: {
    login() {
      this.$router.push({ path: "/login" });
    },
  },
};
</script>

<style></style>
