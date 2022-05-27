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

    <div class="text-h5 font-weight-bold pa-4">ROLE: Receptionist</div>
    <div v-if="user" class="text-h6 pa-4">
      <p>User Name: {{ user.userName }}</p>
      <p>Working Testing Site: {{ user.testingSite.name }}</p>
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
            Phone Calls Modification
          </v-card>
        </v-hover>
      </div>
      <div class="d-flex justify-center pa-12">
        <v-hover v-slot="{ hover }">
          <v-card
            class="pa-8 text-h6 text-center"
            @click="onsiteBooking"
            :style="[
              hover
                ? { outline: '3px solid black' }
                : { outline: '.5px solid black' },
            ]"
            min-width="450"
          >
            On-Site Booking
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
      <div class="d-flex justify-center pa-12">
        <v-hover v-slot="{ hover }">
          <v-card
            class="pa-8 text-h6 text-center"
            @click="updateTestKit"
            :style="[
              hover
                ? { outline: '3px solid black' }
                : { outline: '.5px solid black' },
            ]"
            min-width="450"
          >
            Update Issued Test Kit Information
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

  mounted() {
    this.getUserDetails();
  },

  methods: {
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/search`,
      });
    },
    onsiteBooking() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/onsite-booking`,
      });
    },
    checkStatus() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/check-status`,
      });
    },
    updateTestKit() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/update-test-kit-issued`,
      });
    },
    profile() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/phone-call-profile`,
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
