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
      <div class="text-h5 font-weight-bold pa-4">On-Site Testing</div>
      <div class="text-subtitle-1 px-4">
        Covid Test Verification and Suggestion
      </div>
      <v-row v-if="successMessage">
        <v-col md="6">
          <v-card class="ma-4 pa-4" tile>
            <div class="pa-4 text-subtitle-2 success--text">
              Successfully verify user with PIN Code.
            </div>
            <v-card-title>Suggested Test Type : {{ type }}</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn @click="successMessage = !successMessage"> BACK </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
      <v-col md="8" v-else>
        <v-card class="ma-4 pa-4" tile>
          <div>
            <v-card-title>Interview Form</v-card-title>
            <div class="pa-4">
              <v-form ref="covidTestForm" v-model="valid">
                <v-row>
                  <v-col md="6">
                    <v-text-field
                      v-model="smsPin"
                      label="PIN Code"
                      :rules="mandatoryRule"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col md="6">
                    <v-text-field
                      v-model="patientId"
                      label="Patient ID"
                      :rules="mandatoryRule"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-checkbox
                      v-model="checkbox1"
                      label="Are you exhibiting 2 or more symptoms as listed below? "
                    ></v-checkbox>
                    <ul>
                      <li>Fever</li>
                      <li>Chills</li>
                      <li>Shivering</li>
                      <li>Body Ache</li>
                      <li>Sore throat</li>
                      <li>Nausea or vomiting</li>
                    </ul>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-checkbox
                      v-model="checkbox2"
                      label="Besides the above, are you exhibiting any of the symptoms listed below?"
                    ></v-checkbox>
                    <ul>
                      <li>Difficulty breathing</li>
                      <li>Loss of smell</li>
                      <li>Cough</li>
                      <li>Loss of taste</li>
                    </ul>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-checkbox
                      v-model="checkbox3"
                      label="Have you had close contact with any confirmed/suspected COVID-19 cases within the last 14 days? "
                    ></v-checkbox>
                  </v-col>
                </v-row>
              </v-form>
            </div>
          </div>
          <v-card-actions class="pa-4">
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid" @click="submit">SUBMIT</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </div>
    <v-snackbar v-model="errorMessage" :timeout="timeout" color="error">
      Fail to verify and suggest COVID test type. Please try again.
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
      successMessage: false,
      errorMessage: false,
      symptomRate: null,
      valid: false,
      patientId: "",
      smsPin: "",
      checkbox1: false,
      checkbox2: false,
      checkbox3: false,
      questions: 3,
      type: "RAT",
      timeout: 2000,
    };
  },
  methods: {
    backToMain() {
      this.$router.push({
        path: `/${this.$route.params.id}/hcworker/`,
      });
    },
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/hcworker/search`,
      });
    },
    logout() {
      this.$router.push({
        path: "/login",
      });
    },
    computeType() {
      if (this.checkbox1 || this.checkbox2) {
        this.type = "PCR";
      }
    },
    async submit() {
      this.computeType();
      try {
        await axios.post("/user/covid-test", {
          smsPin: this.smsPin,
          patientId: this.patientId,
          type: this.type,
        });
        this.successMessage = true;
      } catch {
        // handle error
        console.log("Fail to verify and suggest COVID test type.");
        this.errorMessage = true;
      }
      this.$refs.covidTestForm.reset();
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
