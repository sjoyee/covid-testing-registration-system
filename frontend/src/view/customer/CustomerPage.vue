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
    <div v-if="user" class="text-h6 pa-4">
      <p>User Name: {{ user.userName }}</p>
    </div>

    <v-card flat tile>
      <div class="d-flex flex-wrap justify-center pa-12">
        <v-hover v-slot="{ hover }">
          <v-card
            class="pa-8 text-h6 text-center"
            @click="profile"
            :style="[
              hover
                ? { outline: '3px solid black' }
                : { outline: '.5px solid black' },
            ]"
            min-width="450"
          >
            Booking Profile
          </v-card>
        </v-hover>
      </div>
      <div class="d-flex flex-wrap justify-center pa-12">
        <v-hover v-slot="{ hover }">
          <v-card
            class="pa-8 text-h6 text-center"
            @click="modifyBooking"
            :style="[
              hover
                ? { outline: '3px solid black' }
                : { outline: '.5px solid black' },
            ]"
            min-width="450"
          >
            Modify Active Booking By ID
          </v-card>
        </v-hover>
      </div>
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
import axios from "axios";
export default {
  data() {
    return {
      user: null,
    };
  },
  watch: {},
  mounted() {
    this.getUserDetails();
  },
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
    profile() {
      this.$router.push({
        path: `/${this.$route.params.id}/customer/booking-profile`,
      });
    },

    modifyBooking() {
      this.$router.push({
        path: `/${this.$route.params.id}/customer/modify-booking`,
      });
    },
    async logout() {
      await this.$router.push({
        path: "/login",
      });
    },
    async getUserDetails() {
      try {
        const response = await axios.get(`/user/${this.$route.params.id}`);
        this.user = response.data;
      } catch {
        // handle error
        console.log("Fail to create user.");
      }
    },
  },
};
</script>

<style></style>
