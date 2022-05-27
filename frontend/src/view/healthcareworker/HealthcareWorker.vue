<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="search">
        <v-icon left>mdi-map-search</v-icon>
        SEARCH TESTING SITES
      </v-btn>
      <v-btn text @click="logout">LOG OUT</v-btn>
    </v-toolbar>

    <div class="text-h5 font-weight-bold pa-4">ROLE: Healthcare Worker</div>

    <v-card flat tile class="pa-12">
      <div class="d-flex justify-center pa-12">
        <v-hover v-slot="{ hover }">
          <v-card
            class="pa-8 text-h6 text-center"
            @click="onsiteTesting"
            :style="[
              hover
                ? { outline: '3px solid black' }
                : { outline: '.5px solid black' },
            ]"
            min-width="450"
          >
            Covid Test Verification and Suggestion
          </v-card>
        </v-hover>
      </div>
    </v-card>
  </div>
</template>

<script>
export default {
  props: {},
  watch: {},
  methods: {
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/hcworker/search`,
      });
    },
    onsiteTesting() {
      this.$router.push({
        path: `/${this.$route.params.id}/hcworker/onsite-testing`,
      });
    },
    async logout() {
      await this.$router.push({
        path: "/login",
      });
    },
  },
};
</script>

<style></style>
