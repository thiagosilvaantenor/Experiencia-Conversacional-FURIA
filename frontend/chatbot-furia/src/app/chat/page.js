'use client';
import ChatForm from "./ChatForm";
import Image from 'next/image';

export default function ChatPage() {
  return(
    <main className="p-5">
        <div className={"flex flex-col items-center mb-4"}>
            <Image src={"/furia-logo.png"} alt="Logo da FURIA" width={55} height={32} />
        <h1 className="text-xl text-center font-bold mb-4">
            Bem-vindo ao Chat Bot do time de CS da FURIA
        </h1>
        </div>
        <ChatForm/>
    </main>
  );
}
