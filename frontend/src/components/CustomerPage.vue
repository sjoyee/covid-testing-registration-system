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

    <div class="text-h5 font-weight-bold pa-4">ROLE: Customer</div>

    <v-card flat tile class="pa-12">
      <div class="d-flex justify-center pa-12">
        <v-hover v-slot="{ hover }">
          <v-card
            class="pa-8 text-h6 text-center"
            @click="homeBooking"
            :style="[
              hover
                ? { outline: '3px solid black' }
                : { outline: '.5px solid black' },
            ]"
            min-width="450"
          >
            Home Booking
          </v-card>
        </v-hover>
      </div>
      <div class="d-flex justify-center pa-12">
        <v-hover v-slot="{ hover }">
          <v-card
            class="pa-8 text-h6 text-center"
            @click="checkStatus"
            :style="[
              hover
                ? { outline: '3px solid black' }
                : { outline: '.5px solid black' },
            ]"
            min-width="450"
          >
            Check Status With PIN Code
          </v-card>
        </v-hover>
      </div>
    </v-card>
  </div>
</template>

<script>
export default {
  methods: {
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/customer/search`,
      });
    },
    homeBooking() {
      this.$router.push({
        path: `/${this.$route.params.id}/customer/home-booking`,
      });
    },
    checkStatus() {
      this.$router.push({
        path: `/${this.$route.params.id}/customer/check-status`,
      });
    },
    logout() {
      this.$router.push({
        path: "/login",
      });
    },
  },
};
</script>

<style></style>
