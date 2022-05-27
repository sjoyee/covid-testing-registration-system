<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="backToMain">
        <v-icon left>mdi-arrow-left-bold</v-icon>
        BACK TO MAIN
      </v-btn>
      <v-btn text @click="search">
        <v-icon left>mdi-map-search</v-icon>
        SEARCH TESTING SITES
      </v-btn>
      <v-btn text @click="logout">LOG OUT</v-btn>
    </v-toolbar>
    <div class="ma-4">
      <div class="text-h5 font-weight-bold pa-4">Check Status</div>
      <v-row v-if="success">
        <v-col md="6">
          <v-card class="ma-4 pa-4" tile>
            <div class="pa-4 text-subtitle-2 success--text">
              Successfully found PIN Code.
            </div>
            <v-card-title>STATUS : {{ status }}</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn @click="success = !success"> CHECK OTHER PIN CODE </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>

      <v-row v-else>
        <v-col md="6">
          <v-card class="ma-4 pa-4" tile>
            <v-card-subtitle
              >Please request the PIN code from the customer which is sent
              through SMS.</v-card-subtitle
            >
            <v-form ref="form" v-model="valid">
              <v-row class="pa-4">
                <v-col md="6">
                  <v-text-field
                    v-model="smsPin"
                    label="PIN Code"
                    :rules="mandatoryRule"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-form>
            <v-card-actions class="pa-4">
              <v-spacer></v-spacer>
              <v-btn :disabled="!valid" @click="checkPin">CHECK STATUS</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>
    <v-snackbar v-model="errorMessage" :timeout="timeout" color="error">
      Invalid PIN Code.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="errorMessage = false"> Close </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      form: null,
      valid: false,
      smsPin: "",
      success: false,
      errorMessage: false,
      timeout: 2000,
      status: "",
    };
  },
  methods: {
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/search`,
      });
    },
    backToMain() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/`,
      });
    },
    async logout() {
      // TODO
      // call api to unsubscribe

      await this.$router.push({
        path: "/login",
      });
    },
    async checkPin() {
      try {
        const response = await axios.get(
          `/${this.$route.params.id}/booking/check-status?pin=${this.smsPin}`
        );
        this.status = response.data;
        if (this.status == "INVALID") {
          this.errorMessage = true;
        } else {
          this.success = true;
        }
      } catch {
        // handle error
        console.log("Fail to check status with this PIN code.");
        this.errorMessage = true;
      }
      this.$refs.form.reset();
    },
  },
  computed: {
    mandatoryRule() {
      return [(v) => !!v || "This field is mandatory."];
    },
  },
};
</script>

<style></style>
