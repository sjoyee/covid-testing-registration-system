<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="search">
        <v-icon left>mdi-map-search</v-icon>
        SEARCH TESTING SITES
      </v-btn>
      <v-btn text>LOG OUT</v-btn>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  methods: {
    search() {},
  },
};
</script>

<style></style>
