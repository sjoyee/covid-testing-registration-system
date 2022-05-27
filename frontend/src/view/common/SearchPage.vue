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
    <v-card flat tile class="pa-4 ma-4">
      <div class="text-h5 font-weight-bold pa-4">Search Testing Sites</div>
      <v-row class="ma-4" align="center">
        <v-row align="center">
          <v-col class="d-flex" md="6">
            <v-select
              v-model="selectType"
              :items="types"
              item-text="name"
              item-value="value"
              label="Testing Site Type"
              outlined
              return-object
            ></v-select>
          </v-col>
          <v-col class="d-flex" md="6">
            <v-select
              v-model="selectSuburb"
              :items="suburbs"
              item-text="name"
              item-value="value"
              label="Suburb"
              outlined
              return-object
            ></v-select>
          </v-col>
          <v-col>
            <v-btn @click="filterTestingSites">FILTER</v-btn>
          </v-col>
        </v-row>
      </v-row>
    </v-card>
    <v-card flat tile class="ma-4">
      <div v-for="site in sites" :key="site.id">
        <v-col md="10">
          <v-card class="ma-4 pa-4">
            <p>Name: {{ site.name }}</p>
            <p>Description: {{ site.description }}</p>
            <p>Website Url: {{ site.websiteUrl }}</p>
            <p>Phone Number: {{ site.phoneNumber }}</p>
            <p>Site Type: {{ site.testingSiteType }}</p>
            <p>Suburb: {{ site.address.suburb }}</p>
            <p>Waiting Time: {{ site.waitingTime }} minutes</p>
            <p>Open Time: {{ site.openTime }}</p>
            <p>Close Time: {{ site.closeTime }}</p>
          </v-card>
        </v-col>
      </div>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      isLoggedIn: false,
      types: [
        { name: "All", value: "" },
        { name: "Walk-In", value: "WALKIN" },
        { name: "Drive Through", value: "DRIVETHROUGH" },
        { name: "Clinic", value: "CLINIC" },
        { name: "GP", value: "GP" },
        { name: "Hospital", value: "HOSPITAL" },
      ],
      suburbs: [
        { name: "All", value: "" },
        { name: "Clayton", value: "Clayton" },
        { name: "Mount Waverley", value: "Mount Waverley" },
        { name: "Petaling Jaya", value: "Petaling Jaya" },
        {
          name: "Bangsar",
          value: "Bangsar",
        },
      ],
      selectType: { name: "All", value: "" },
      selectSuburb: { name: "All", value: "" },
      sites: [],
    };
  },
  created() {
    this.checkLoggedIn();
    this.filterTestingSites();
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
    async filterTestingSites() {
      try {
        const response = await axios.get(
          `/testing-site?type=${this.selectType.value}&suburb=${this.selectSuburb.value}`
        );
        this.sites = response.data;
      } catch {
        console.log("Fail to get testing sites.");
      }
    },
  },
  computed: {},
};
</script>

<style></style>
