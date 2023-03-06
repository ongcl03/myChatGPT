# Author  : Ong Chang Le
# Date    : 02/03/2023
# Project : Python with ChatGPT Api


import openai


# Use your openai api key (https://platform.openai.com/account/api-keys)
openai.api_key = "sk-..."

MODEL = "gpt-3.5-turbo"

# see https://platform.openai.com/account/usage for the free api trial usage remaining

# Set up the basic role for ChatGPT
messages=[{"role": "system", "content": "You are a helpful assistant, and you will correctly answer all my questions."}]


# Ask ChatGPT question and get reply
def chat_with_ChatGPT():

    # Input the question
    my_question = input("😪 : ") 

    # It will append to the messages list as a user role (your question)
    messages.append({"role": "user", "content": my_question})

    # Ask ChatGPT and get reply
    response = openai.ChatCompletion.create(model=MODEL, messages= messages)

    # It will return as a key, value pair and now just get the reply
    reply_message = response['choices'][0]['message']['content']
    total_tokens = response['usage']['total_tokens']
    messages.append({"role": "assistant", "content": reply_message})

    # print("total token:", total_tokens)
    print("🤖 :", reply_message)
    print('')
    

while True:
   chat_with_ChatGPT()
